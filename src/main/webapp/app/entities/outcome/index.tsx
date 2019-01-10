import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Outcome from './outcome';
import OutcomeDetail from './outcome-detail';
import OutcomeUpdate from './outcome-update';
import OutcomeDeleteDialog from './outcome-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OutcomeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OutcomeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OutcomeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Outcome} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OutcomeDeleteDialog} />
  </>
);

export default Routes;
