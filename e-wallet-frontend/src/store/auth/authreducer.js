import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import * as actionType from './authtype'
import baserequest from '../../core/baserequest';
const initialState = {
    user: [],
    transc: []
}

export const fetchUserAsync = createAsyncThunk ("auth/fetchuser",async() => {
    const res = await baserequest.post('auth/userDetail');
    
    return res.data;
})


// const authreducer = (state = INNITIAL_STATES,action) =>{
//     switch(action.type){
//         case actionType.UPDATE_USER_STATE:
//             //console.log(action.payload)
//             return {
//                 ...state,
//                 user: action.payload
//             }

//         default:
//             return state
//     }

// }


const authSlice = createSlice({
    name:'auth',
    initialState,
    reducers:{
        UPDATE_USER_STATE: (state,action) => {
            state.user = action.payload;
        },
        REMOVE_USER_STATE: (state,action) => {
            state.user = [];
        }
    },
    extraReducers:{
        [fetchUserAsync.fulfilled]: (state,action) => {
            
            const s =  {...state,user:action.payload}
            console.log(s);
            return s;
        }
    }
})

export const {UPDATE_USER_STATE,REMOVE_USER_STATE} = authSlice.actions;

export default authSlice.reducer;