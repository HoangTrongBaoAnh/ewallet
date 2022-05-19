import { combineReducers } from "redux";
import authreducer from './auth/authreducer'

const rootReducer = combineReducers({auth: authreducer});

export default rootReducer;