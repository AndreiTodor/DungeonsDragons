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
            <Grid item xs={2}>
                <b>Name</b>
            </Grid>
            <Grid item xs={10}>
                {character.name}
            </Grid>
            <Grid item xs={2}>
                <b>Age</b>
            </Grid>
            <Grid item xs={10}>
                {character.age}
            </Grid>
            <Grid item xs={2}>
                <b>Race</b>
            </Grid>
            <Grid item xs={10}>
                {character.ddRace.name}
            </Grid>
            <Grid item xs={2}>
                <b>Class</b>
            </Grid>
            <Grid item xs={10}>
                {character.ddClass.name}
            </Grid>
            <Grid item xs={2}>
                <b>Equipments</b>
            </Grid>
            <Grid item xs={10}>
                {character.equipments.map(eq => (
                    <Typography key={eq.id}>{`${eq.quantity || '-'} x ${eq.item.name}`}</Typography>
                ))}
            </Grid>
        </Grid>
    );
};

export default CharacterInfo;
