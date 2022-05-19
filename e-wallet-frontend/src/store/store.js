// import {createStore,compose,applyMiddleware} from 'redux';
// import DevTools from './devtool'
import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './rootreducer';
// import { composeWithDevTools,devToolsEnhancer  } from '@redux-devtools/extension';
// const enhancer = compose(
//     // Middleware you want to use in development:
//     applyMiddleware(d1, d2, d3),
//     // Required! Enable Redux DevTools with the monitors you chose
//     DevTools.instrument()
//   );

//   export default function configureStore(initialState) {
// Note: only Redux >= 3.1.0 supports passing enhancer as third argument.
// See https://github.com/reactjs/redux/releases/tag/v3.1.0
const store = configureStore({ reducer: rootReducer });


export default store;
