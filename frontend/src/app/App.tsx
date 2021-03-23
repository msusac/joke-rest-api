import React from 'react';
import { Route } from 'react-router-dom';
import HomePage from './features/HomePage/HomePage';

const App = () => {

  return(
    <>
      <Route exact path="/" component={HomePage} />
    </>
  );
}
export default App;
