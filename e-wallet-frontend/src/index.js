import React from 'react';
import ReactDOM from 'react-dom';
import Routerx from './router/router'
import 'bootstrap/dist/css/bootstrap.min.css';
import {Provider} from 'react-redux'
import store from './store/store'

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
    <Routerx />
    </Provider>
    
  </React.StrictMode>,
  document.getElementById('root')
);
