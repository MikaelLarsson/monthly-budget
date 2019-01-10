import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOutcome, defaultValue } from 'app/shared/model/outcome.model';

export const ACTION_TYPES = {
  FETCH_OUTCOME_LIST: 'outcome/FETCH_OUTCOME_LIST',
  FETCH_OUTCOME: 'outcome/FETCH_OUTCOME',
  CREATE_OUTCOME: 'outcome/CREATE_OUTCOME',
  UPDATE_OUTCOME: 'outcome/UPDATE_OUTCOME',
  DELETE_OUTCOME: 'outcome/DELETE_OUTCOME',
  RESET: 'outcome/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOutcome>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OutcomeState = Readonly<typeof initialState>;

// Reducer

export default (state: OutcomeState = initialState, action): OutcomeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OUTCOME_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OUTCOME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_OUTCOME):
    case REQUEST(ACTION_TYPES.UPDATE_OUTCOME):
    case REQUEST(ACTION_TYPES.DELETE_OUTCOME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_OUTCOME_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OUTCOME):
    case FAILURE(ACTION_TYPES.CREATE_OUTCOME):
    case FAILURE(ACTION_TYPES.UPDATE_OUTCOME):
    case FAILURE(ACTION_TYPES.DELETE_OUTCOME):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_OUTCOME_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_OUTCOME):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_OUTCOME):
    case SUCCESS(ACTION_TYPES.UPDATE_OUTCOME):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_OUTCOME):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/outcomes';

// Actions

export const getEntities: ICrudGetAllAction<IOutcome> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OUTCOME_LIST,
  payload: axios.get<IOutcome>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOutcome> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OUTCOME,
    payload: axios.get<IOutcome>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOutcome> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OUTCOME,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOutcome> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OUTCOME,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOutcome> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OUTCOME,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
