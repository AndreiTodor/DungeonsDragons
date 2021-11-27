import { makeStyles, Paper, Typography } from '@material-ui/core';
import React from 'react';
import CustomSelect from './wrappers/CustomSelect';

const useStyles = makeStyles(theme => ({
    chooseEquipemntCard: {
        display: 'flex',
        flexDirection: 'column',
        padding: 10,
        marginBottom: 30,
        backgroundColor: theme.palette.grey[200],
    },
    marginBottom: {
        marginBottom: 10,
    },
    selectedOption: {
        backgroundColor: theme.palette.grey[400],
        marginBottom: 10,
        '&:hover': {
            cursor: 'pointer',
        },
    },
    option: {
        backgroundColor: 'white',
        marginBottom: 10,
        '&:hover': {
            cursor: 'pointer',
        },
    },
    optionForm: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    optionSelect: {
        flexGrow: 1,
        marginLeft: 10,
        marginRight: 10,
    },
}))

const StarterEquipment = ({starter, selectedBundles, onSelectBundle, selectedCategoryOptions, onSelectCategoryItem}) => {
    const classes = useStyles();
    return (
        <Paper key={starter.id} className={classes.chooseEquipemntCard} elevation={3}>
            <Typography className={classes.marginBottom}>
                {`Select ${starter.choose} from:`}
            </Typography>
            {starter.bundles.map(bundle => {
                const isSelected = selectedBundles.some(
                    pair => pair.bundleId === bundle.id && pair.starterId === starter.id
                );
                return (
                    <Paper
                        className={isSelected ? classes.selectedOption : classes.option}
                        onClick={onSelectBundle(bundle, starter)}
                        key={bundle.id}
                    >
                        <ul>
                            {bundle.equipments.map(eq => (
                                <li key={eq.id}>
                                    <Typography>{`${eq?.quantity || '-'} x ${
                                        eq.item?.name || '-'
                                    }`}</Typography>
                                </li>
                            ))}
                            {bundle.categoryEquipmentOptions.map(category => {
                                const res = selectedCategoryOptions.find(
                                    pair => pair.categoryId === category.id
                                );
                                const itemIndex = res?.itemIndex || '';
                                return (
                                    <li key={category.id}>
                                        <div className={classes.optionForm}>
                                            <Typography>
                                                {`${category?.choose || '-'} x `}
                                            </Typography>
                                            <CustomSelect
                                                name={`category-${category.id}`}
                                                value={itemIndex}
                                                onChange={onSelectCategoryItem(category)}
                                                options={
                                                    category?.equipmentCategory?.equipmentItems ||
                                                    []
                                                }
                                                displayEmpty
                                                placeholder={category?.equipmentCategory?.name}
                                                className={classes.optionSelect}
                                            />
                                        </div>
                                    </li>
                                );
                            })}
                        </ul>
                    </Paper>
                );
            })}
        </Paper>
    );
};

export default StarterEquipment;
