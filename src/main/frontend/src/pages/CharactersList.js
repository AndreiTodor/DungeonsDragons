import { Box, Button, makeStyles, Typography } from '@material-ui/core';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CharacterInfo from '../components/CharacterInfo';

const useStyles = makeStyles(theme => ({
    title: {
        marginBottom: 60,
    },
}));

const CharactersList = () => {
    const classes = useStyles();
    const navigate = useNavigate();
    const [characters, setCharacters] = useState([]);

    useEffect(() => {
        const fetchCharacters = async () => {
            const { data } = await axios.get('/characters', {
                baseURL: process.env.REACT_APP_API_URL,
            });
            setCharacters(data);
        };
        fetchCharacters();
    }, []);

    return (
        <Box margin={2} display="flex" flexDirection="column" width="50%">
            <Typography variant="h3" className={classes.title}>
                Characters List
            </Typography>
            {characters.map(c => <CharacterInfo character={c} />)}
            <Button variant="contained" onClick={() => navigate('/create-character')}>
                New character
            </Button>
        </Box>
    );
};

export default CharactersList;
