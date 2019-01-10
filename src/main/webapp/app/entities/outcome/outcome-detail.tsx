import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './outcome.reducer';
import { IOutcome } from 'app/shared/model/outcome.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOutcomeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OutcomeDetail extends React.Component<IOutcomeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { outcomeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Outcome [<b>{outcomeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="amount">Amount</span>
            </dt>
            <dd>{outcomeEntity.amount}</dd>
            <dt>Budget</dt>
            <dd>{outcomeEntity.budget ? outcomeEntity.budget.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/outcome" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/outcome/${outcomeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ outcome }: IRootState) => ({
  outcomeEntity: outcome.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OutcomeDetail);
