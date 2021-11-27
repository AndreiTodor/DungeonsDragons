import { Grid, makeStyles, Typography } from '@material-ui/core';
import React from 'react';

const useStyles = makeStyles(theme => ({
    characterInfo: {
        marginBottom: 20,
        '&:not(:last-child)': {
            borderBottom: '2px solid',
        },
    },
}));

const CharacterInfo = ({ character }) => {
    const classes = useStyles();
    return (
        <Grid container className={classes.characterInfo}>
            <Grid item lg={2} sm={4} xs={6}>
                <b>Name</b>
            </Grid>
            <Grid item lg={10} sm={8} xs={6}>
                {character.name}
            </Grid>
            <Grid item lg={2} sm={4} xs={6}>
                <b>Age</b>
            </Grid>
            <Grid item lg={10} sm={8} xs={6}>
                {character.age}
            </Grid>
            <Grid item lg={2} sm={4} xs={6}>
                <b>Race</b>
            </Grid>
            <Grid item lg={10} sm={8} xs={6}>
                {character.ddRace.name}
            </Grid>
            <Grid item lg={2} sm={4} xs={6}>
                <b>Class</b>
            </Grid>
            <Grid item lg={10} sm={8} xs={6}>
                {character.ddClass.name}
            </Grid>
            <Grid item lg={2} sm={4} xs={6}>
                <b>Equipments</b>
            </Grid>
            <Grid item lg={10} sm={8} xs={6}>
                {character.equipments.map(eq => (
                    <Typography key={eq.id}>{`${eq.quantity || '-'} x ${eq.item.name}`}</Typography>
                ))}
            </Grid>
        </Grid>
    );
};

export default CharacterInfo;
