import React from 'react';
import { Button, Container, Divider, Header, Item, Segment } from 'semantic-ui-react';

const HomePage = () => {

    const randomJokeStyle = {
        fontStyle: 'italic'
    };

    return (
        <Segment
            inverted
            textAlign='center'
            vertical
            className='masthead'>

            <Container>
                <Header as='h1' inverted>
                    Spring/React Joke App
                </Header>
                <Header as='h2' inverted style={randomJokeStyle}>
                    Random Joke!
                </Header>
                <Divider />
                <Header>
                    <Button
                        inverted
                        size='huge'>
                        Visit Joke App
                    </Button>
                </Header>
            </Container>
        </Segment>
    );
}

export default HomePage;