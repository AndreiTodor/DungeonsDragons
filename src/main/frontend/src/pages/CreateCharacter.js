import { Box, Button, makeStyles, Typography } from '@material-ui/core';
import axios from 'axios';
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import StarterEquipment from '../components/StarterEquipment';
import CustomInput from '../components/wrappers/CustomInput';
import CustomSelect from '../components/wrappers/CustomSelect';

const useStyles = makeStyles(theme => ({
    form: {
        marginTop: 40,
        display: 'flex',
        flexDirection: 'column',
        width: '40%',
        [theme.breakpoints.down('md')]: {
            width: '50%',
        },
        [theme.breakpoints.down('sm')]: {
            width: '80%',
        },
        [theme.breakpoints.down('xs')]: {
            width: '100%',
        },
    },
    marginBottom: {
        marginBottom: 10,
    },
}));

const starterIsNotEmpty = starter => {
    const allItemNumber = starter.bundles.reduce((acc, curr) => {
        return acc + curr.equipments.length + curr.categoryEquipmentOptions.length;
    }, 0);
    return allItemNumber > 0;
};

const CreateCharacter = () => {
    const classes = useStyles();
    const navigate = useNavigate();
    const [character, setCharacter] = useState({
        name: '',
        age: '',
        selectedRace: '',
        selectedClass: null,
    });
    const [allClasses, setAllClasses] = useState([]);
    const [allRaces, setAllRaces] = useState([]);
    const [selectedCategoryOptions, setSelectedCategoryOptions] = useState([]); // [{categoryId: 2, itemIndex: 'weapon'}, {...}, ...]
    const [selectedBundles, setSelectedBundles] = useState([]); // [{starterId: 5, bundleId: 10}, {...}, ...]

    const canSubmit = useMemo(() => {
        const allStarterOptionsSelected =
            character?.selectedClass?.starterEquipmentOptions?.every(
                starter =>
                    !starterIsNotEmpty(starter) ||
                    !!selectedBundles.find(pair => pair.starterId === starter.id)
            ) || false;
        return (
            !!character.name &&
            parseInt(character.age) > 0 &&
            !!character.selectedRace &&
            !!character.selectedClass &&
            allStarterOptionsSelected
        );
    }, [character, selectedBundles]);

    const handleChange = useCallback(
        e => {
            const { name, value } = e.target;
            switch (name) {
                case 'age':
                    const noSignValue = value.replace(/[^\d]/g, '');
                    setCharacter(prevState => ({ ...prevState, age: noSignValue }));
                    break;
                case 'selectedRace':
                    const selectedRace = allRaces.find(r => r.index === value);
                    setCharacter(prevState => ({ ...prevState, selectedRace }));
                    break;
                case 'selectedClass':
                    const selectedClass = allClasses.find(c => c.index === value);
                    setCharacter(prevState => ({ ...prevState, selectedClass }));
                    setSelectedBundles([]);
                    setSelectedCategoryOptions([]);
                    break;
                default:
                    setCharacter(prevState => ({ ...prevState, [name]: value }));
            }
        },
        [allClasses, allRaces]
    );

    const handleSelectBundle = (bundle, starterOption) => () => {
        const alreadySelectioned = selectedBundles.find(
            pair => pair.bundleId === bundle.id && pair.starterId === starterOption.id
        );
        if (alreadySelectioned) {
            return;
        }

        let allCategoriesFilled = true;
        if (bundle.categoryEquipmentOptions.length > 0) {
            allCategoriesFilled = bundle.categoryEquipmentOptions.every(category =>
                selectedCategoryOptions.find(pair => pair.categoryId === category.id)
            );
        }

        if (allCategoriesFilled) {
            const otherBundlesFromSameStarter = selectedBundles.filter(
                pair => pair.starterId === starterOption.id
            );
            if (otherBundlesFromSameStarter.length >= starterOption.choose) {
                setSelectedBundles(prevState => {
                    // remove the first one
                    const newSelectedBundles = selectedBundles.filter(
                        pair =>
                            pair.starterId !== otherBundlesFromSameStarter?.[0]?.starterId ||
                            pair.bundleId !== otherBundlesFromSameStarter?.[0]?.bundleId
                    );
                    return [
                        ...newSelectedBundles,
                        { starterId: starterOption.id, bundleId: bundle.id },
                    ];
                });
            } else {
                setSelectedBundles(prevState => [
                    ...prevState,
                    { starterId: starterOption.id, bundleId: bundle.id },
                ]);
            }
        }
    };

    const handleSelectItem = category => e => {
        const value = e.target.value;
        setSelectedCategoryOptions(prevState => {
            const index = prevState.findIndex(pair => pair.categoryId === category.id);
            if (index >= 0) {
                return [
                    ...prevState.slice(0, index),
                    { categoryId: category.id, itemIndex: value },
                    ...prevState.slice(index + 1),
                ];
            }
            return [...prevState, { categoryId: category.id, itemIndex: value }];
        });
    };

    const handleSubmit = useCallback(
        e => {
            const save = async payload => {
                await axios.post('/characters', payload, {
                    baseURL: process.env.REACT_APP_API_URL,
                });
                // reset the form
                setCharacter({ name: '', age: '', selectedRace: null, selectedClass: null });
                setSelectedCategoryOptions([]);
                setSelectedBundles([]);
            };

            const newEquipments =
                character.selectedClass?.starterEquipmentOptions?.flatMap(starter => {
                    const bundleIdList = selectedBundles
                        .filter(pair => pair.starterId === starter.id)
                        .map(pair => pair.bundleId);
                    return starter.bundles
                        .filter(b => bundleIdList.includes(b.id))
                        .flatMap(b => {
                            const equipmentsWithoutId = b.categoryEquipmentOptions
                                .map(cat => {
                                    const itemSelected = selectedCategoryOptions.find(
                                        pair => pair.categoryId === cat.id
                                    );
                                    if (itemSelected) {
                                        return {
                                            item: { index: itemSelected.itemIndex },
                                            quantity: cat.choose,
                                        };
                                    }
                                    return null;
                                })
                                .filter(item => item);
                            return [...b.equipments, ...equipmentsWithoutId];
                        });
                }) || [];
            const allEquipments = [
                ...character.selectedClass?.startingEquipments,
                ...newEquipments,
            ];
            const payload = {
                name: character.name,
                age: character.age,
                ddRace: character.selectedRace.index,
                ddClass: character.selectedClass.index,
                equipments: allEquipments,
            };
            save(payload);
        },
        [character, selectedBundles, selectedCategoryOptions]
    );

    useEffect(() => {
        const fetchClassesAndRaces = async () => {
            const { data: classData } = await axios.get('/classes', {
                baseURL: process.env.REACT_APP_API_URL,
            });
            setAllClasses(classData);
            const { data: raceData } = await axios.get('/races', {
                baseURL: process.env.REACT_APP_API_URL,
            });
            setAllRaces(raceData);
        };
        fetchClassesAndRaces();
    }, []);

    return (
        <Box margin={2} display="flex" flexDirection="column">
            <Typography variant="h3">Create character</Typography>
            <form className={classes.form} autoComplete="off">
                <CustomInput
                    name="name"
                    label="Name"
                    value={character.name}
                    onChange={handleChange}
                />
                <CustomInput name="age" label="Age" value={character.age} onChange={handleChange} />
                <CustomSelect
                    name="selectedRace"
                    label="Race"
                    value={character?.selectedRace?.index || ''}
                    onChange={handleChange}
                    options={allRaces}
                />
                <CustomSelect
                    name="selectedClass"
                    label="Class"
                    value={character?.selectedClass?.index || ''}
                    onChange={handleChange}
                    options={allClasses}
                />
                {!!character.selectedClass && (
                    <Box>
                        <Typography>Equipment</Typography>
                        <ul>
                            {character.selectedClass.startingEquipments?.map(eq => (
                                <li key={eq.id}>
                                    <Typography>{`${eq?.quantity || '-'} x ${
                                        eq.item?.name || '-'
                                    }`}</Typography>
                                </li>
                            ))}
                        </ul>
                        {character.selectedClass.starterEquipmentOptions
                            ?.filter(option => starterIsNotEmpty(option))
                            .map(starter => (
                                <StarterEquipment
                                    starter={starter}
                                    selectedBundles={selectedBundles}
                                    onSelectBundle={handleSelectBundle}
                                    selectedCategoryOptions={selectedCategoryOptions}
                                    onSelectCategoryItem={handleSelectItem}
                                />
                            ))}
                    </Box>
                )}
                <Button
                    variant="contained"
                    disabled={!canSubmit}
                    onClick={handleSubmit}
                    className={classes.marginBottom}
                >
                    Create
                </Button>
                <Button variant="contained" color="primary" onClick={() => navigate('/')}>
                    Back
                </Button>
            </form>
        </Box>
    );
};

export default CreateCharacter;
