import { Box, Button, makeStyles, Typography } from '@material-ui/core';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CharacterInfo from '../components/CharacterInfo';

const useStyles = makeStyles(theme => ({
    title: {
        marginBottom: 60,
    },
    pageContent: {
        display: 'flex',
        flexDirection: 'column',
        width: '50%',
        [theme.breakpoints.down('sm')]: {
            width: '100%',
        },
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
        <Box margin={2} display="flex" flexDirection="column">
            <Typography variant="h3" className={classes.title}>
                Characters List
            </Typography>
            <Box className={classes.pageContent}>
                {characters.map(c => (
                    <CharacterInfo character={c} />
                ))}
                <Button variant="contained" onClick={() => navigate('/create-character')}>
                    New character
                </Button>
            </Box>
        </Box>
    );
};

export default CharactersList;
