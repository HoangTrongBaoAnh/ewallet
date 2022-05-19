import * as actionType from './authtype'

export const updateUser = (item) => {
    return {
        type: actionType.UPDATE_USER_STATE,
        payload: item
    }
}