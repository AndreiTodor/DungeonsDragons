import {
    FormControl,
    FormLabel,
    makeStyles,
    MenuItem,
    Select,
    withStyles,
} from '@material-ui/core';
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

const StyledSelect = withStyles(theme => ({
    select: {
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
}))(Select);

const CustomSelect = ({
    name,
    label,
    value,
    onChange,
    options,
    className,
    displayEmpty,
    placeholder,
}) => {
    const classes = useStyles();
    return (
        <FormControl variant="outlined" classes={{ root: className || classes.formControl }}>
            {label && <FormLabel className={classes.label}>{label}</FormLabel>}
            <StyledSelect name={name} value={value} onChange={onChange} displayEmpty={displayEmpty}>
                {placeholder && (
                    <MenuItem value="" disabled>
                        {placeholder}
                    </MenuItem>
                )}
                {options.map(r => (
                    <MenuItem key={r.index} value={r.index}>
                        {r.name}
                    </MenuItem>
                ))}
            </StyledSelect>
        </FormControl>
    );
};

export default CustomSelect;
