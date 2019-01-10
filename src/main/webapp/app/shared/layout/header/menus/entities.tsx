import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/budget">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Budget
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/income">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Income
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/outcome">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Outcome
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
