import { FormControl, FormLabel, makeStyles, OutlinedInput, withStyles } from '@material-ui/core';
import React from 'react';

const useStyles = makeStyles(theme => ({
    formControl: {
        width: '100%',
        marginLeft: 0,
        marginBottom: 20,
    },
    label: {
        marginBottom: 10,
    },
}));

const StyledOutlineInput = withStyles(theme => ({
    input: {
        padding: '8px 18px',
        fontSize: '14px',
        lineHeight: 1.75,
        boxSizing: 'border-box',
        height: 41,
    },
    root: {
        borderRadius: 3,
        height: 40,
        width: '100%',
    },
}))(OutlinedInput);

const CustomInput = ({ name, label, value, onChange }) => {
    const classes = useStyles();
    return (
        <FormControl classes={{ root: classes.formControl }}>
            {label && <FormLabel className={classes.label}>{label}</FormLabel>}
            <StyledOutlineInput name={name} value={value} onChange={onChange} />
        </FormControl>
    );
};

export default CustomInput;
