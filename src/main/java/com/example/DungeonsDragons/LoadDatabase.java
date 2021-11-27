package com.example.DungeonsDragons;

import com.example.DungeonsDragons.model.*;
import com.example.DungeonsDragons.repositories.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    CommandLineRunner initDatabase(
            DDEquipmentCategoryRepository equipmentCategoryRepository,
            DDEquipmentItemRepository equipmentItemRepository,
            DDEquipmentRepository equipmentRepository,
            DDCategoryEquipmentOptionRepository categoryEquipmentOptionRepository,
            DDEquipmentBundleRepository equipmentBundleRepository,
            DDStarterEquipmentOptionRepository starterEquipmentOptionRepository,
            DDClassRepository classRepository,
            DDRaceRepository raceRepository,
            RestTemplate restTemplate
    ) {
        return args -> {
            log.info("LOADING OBJECTS IN DB");
            String baseURI = "https://www.dnd5eapi.co";
            JsonNode node = restTemplate.getForObject(baseURI + "/api/equipment-categories", JsonNode.class);
            ArrayNode results = (ArrayNode) node.get("results");
            results.forEach(result -> {
                String url = result.get("url").asText();
                String categoryIndex = result.get("index").asText();
                String categoryName = result.get("name").asText();
                JsonNode categoryNode = restTemplate.getForObject(baseURI + url, JsonNode.class);
                ArrayNode equipmentsNode = (ArrayNode) categoryNode.get("equipment");
                DDEquipmentCategory savedCategory = equipmentCategoryRepository.save(new DDEquipmentCategory(categoryIndex, categoryName));
                List<DDEquipmentItem> categoryEquipments = new ArrayList<>();
                equipmentsNode.forEach(equipment -> {
                    //String equipmentUrl = equipment.get("url").asText();
                    String equipmentIndex = equipment.get("index").asText();
                    String equipmentName = equipment.get("name").asText();
                    DDEquipmentItem existingItem = equipmentItemRepository.findByIndex(equipmentIndex);
                    if (existingItem != null) {
                        categoryEquipments.add(existingItem);
                    } else {
                        DDEquipmentItem savedEquipmentItem = equipmentItemRepository.save(new DDEquipmentItem(equipmentIndex, equipmentName));
                        categoryEquipments.add(savedEquipmentItem);
                    }
                    //JsonNode equipmentNode = restTemplate.getForObject(baseURI + equipmentUrl, JsonNode.class);
                });
                savedCategory.addEquipmentItems(categoryEquipments);
                equipmentCategoryRepository.save(savedCategory);
            });

            node = restTemplate.getForObject(baseURI + "/api/races", JsonNode.class);
            results = (ArrayNode) node.get("results");
            results.forEach(result -> {
                String raceIndex = result.get("index").asText();
                String raceName = result.get("name").asText();
                raceRepository.save(new DDRace(raceIndex, raceName));
            });

            node = restTemplate.getForObject(baseURI + "/api/classes", JsonNode.class);
            results = (ArrayNode) node.get("results");
            results.forEach(ddClassNode -> {
                String url = ddClassNode.get("url").asText();
                JsonNode classNode = restTemplate.getForObject(baseURI + url, JsonNode.class);
                String classIndex = classNode.get("index").asText();
                String className = classNode.get("name").asText();
                ArrayNode startingEquipmentNodes = (ArrayNode) classNode.get("starting_equipment");
                List<DDEquipment> startingEquipments = new ArrayList<>();
                startingEquipmentNodes.forEach(startingEquipmentNode -> {
                    int quantity = startingEquipmentNode.get("quantity").asInt();
                    JsonNode equipmentNode = startingEquipmentNode.get("equipment");
                    String index = equipmentNode.get("index").asText();
                    DDEquipmentItem equipmentItem = equipmentItemRepository.findByIndex(index);
                    if (equipmentItem != null && quantity > 0) {
                        DDEquipment savedEquipment = equipmentRepository.save(new DDEquipment(quantity));
                        savedEquipment.setItem(equipmentItem);
                        startingEquipments.add(equipmentRepository.save(savedEquipment));
                    }
                });

                ArrayNode startingEquipmentOptionsNodes = (ArrayNode) classNode.get("starting_equipment_options");
                List<DDStarterEquipmentOption> starterEquipmentOptions = new ArrayList<>();
                startingEquipmentOptionsNodes.forEach(startingEquipmentOptionsNode -> {
                    List<DDEquipmentBundle> equipmentBundles = new ArrayList<>();
                    int choose = startingEquipmentOptionsNode.get("choose").asInt();
                    ArrayNode fromNodes = (ArrayNode) startingEquipmentOptionsNode.get("from");
                    fromNodes.forEach(fromNode -> {
                        List<DDCategoryEquipmentOption> bundleOptions = new ArrayList<>();
                        List<DDEquipment> bundleEquipments = new ArrayList<>();
                        JsonNode equipmentOptionNode = fromNode.get("equipment_option");
                        JsonNode simpleEquipmentNode = fromNode.get("equipment");
                        if (equipmentOptionNode != null) {
                            int categoryChoose = equipmentOptionNode.get("choose").asInt();
                            JsonNode equipmentCategoryNode = equipmentOptionNode.get("from").get("equipment_category");
                            String equipmentCategoryIndex = equipmentCategoryNode.get("index").asText();
                            DDEquipmentCategory existingCategory = equipmentCategoryRepository.findByIndex(equipmentCategoryIndex);
                            if (existingCategory != null) {
                                DDCategoryEquipmentOption savedCategoryEquipmentOption = categoryEquipmentOptionRepository.save(new DDCategoryEquipmentOption(categoryChoose));
                                savedCategoryEquipmentOption.setEquipmentCategory(existingCategory);
                                bundleOptions.add(categoryEquipmentOptionRepository.save(savedCategoryEquipmentOption));
                            }
                        } else if (simpleEquipmentNode != null) {
                            int quantity = fromNode.get("quantity").asInt();
                            String index = simpleEquipmentNode.get("index").asText();
                            DDEquipmentItem equipmentItem = equipmentItemRepository.findByIndex(index);
                            if (equipmentItem != null && quantity > 0) {
                                DDEquipment savedEquipment = equipmentRepository.save(new DDEquipment(quantity));
                                savedEquipment.setItem(equipmentItem);
                                bundleEquipments.add(equipmentRepository.save(savedEquipment));
                            }
                        } else {
                            Iterator<JsonNode> it = fromNode.elements();
                            while(it.hasNext()) {
                                JsonNode elementNode = it.next();
                                JsonNode itEquipNode = elementNode.get("equipment");
                                JsonNode itEquipOptionNode = elementNode.get("equipment_option");
                                if (itEquipOptionNode != null) {
                                    int categoryChoose = itEquipOptionNode.get("choose").asInt();
                                    JsonNode equipmentCategoryNode = itEquipOptionNode.get("from").get("equipment_category");
                                    String equipmentCategoryIndex = equipmentCategoryNode.get("index").asText();
                                    DDEquipmentCategory existingCategory = equipmentCategoryRepository.findByIndex(equipmentCategoryIndex);
                                    if (existingCategory != null) {
                                        DDCategoryEquipmentOption savedCategoryEquipmentOption = categoryEquipmentOptionRepository.save(new DDCategoryEquipmentOption(categoryChoose));
                                        savedCategoryEquipmentOption.setEquipmentCategory(existingCategory);
                                        bundleOptions.add(categoryEquipmentOptionRepository.save(savedCategoryEquipmentOption));
                                    }
                                } else if (itEquipNode != null) {
                                    int quantity = elementNode.get("quantity").asInt();
                                    String index = itEquipNode.get("index").asText();
                                    DDEquipmentItem equipmentItem = equipmentItemRepository.findByIndex(index);
                                    if (equipmentItem != null && quantity > 0) {
                                        DDEquipment savedEquipment = equipmentRepository.save(new DDEquipment(quantity));
                                        savedEquipment.setItem(equipmentItem);
                                        bundleEquipments.add(equipmentRepository.save(savedEquipment));
                                    }
                                }
                            }
                        }
                        DDEquipmentBundle savedBundle = equipmentBundleRepository.save(new DDEquipmentBundle());
                        savedBundle.setEquipments(bundleEquipments);
                        savedBundle.setCategoryEquipmentOptions(bundleOptions);
                        equipmentBundles.add(equipmentBundleRepository.save(savedBundle));
                    });
                    DDStarterEquipmentOption savedStarterEquipmentOption = starterEquipmentOptionRepository.save(new DDStarterEquipmentOption(choose));
                    savedStarterEquipmentOption.setBundles(equipmentBundles);
                    starterEquipmentOptions.add(starterEquipmentOptionRepository.save(savedStarterEquipmentOption));
                });
                DDClass savedClass = classRepository.save(new DDClass(classIndex, className));
                savedClass.setStartingEquipments(startingEquipments);
                savedClass.setStarterEquipmentOptions(starterEquipmentOptions);
                classRepository.save(savedClass);
            });

            log.info("OBJECTS LOADED IN DB");
        };
    }
}
