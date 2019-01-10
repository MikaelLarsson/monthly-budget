import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Budget from './budget';
import Income from './income';
import Outcome from './outcome';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/budget`} component={Budget} />
      <ErrorBoundaryRoute path={`${match.url}/income`} component={Income} />
      <ErrorBoundaryRoute path={`${match.url}/outcome`} component={Outcome} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
