import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';

class PlanList extends Component {
	 static propTypes = {
		    cookies: instanceOf(Cookies).isRequired
		  };

  constructor(props) {
    super(props);
    const {cookies} = props;
    this.state = {plans: [], csrfToken: cookies.get('XSRF-TOKEN'), isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/plans', {credentials: 'include'})
      .then(response => response.json())
      .then(data => this.setState({plans: data, isLoading: false}));

  }

  async remove(id) {
    await fetch(`/api/plan/${id}`, {
      method: 'DELETE',
      headers: {
    	'X-XSRF-TOKEN': this.state.csrfToken,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      credentials: 'include'
    }).then(() => {
      let updatedPlans = [...this.state.plans].filter(i => i.id !== id);
      this.setState({plans: updatedPlans});
    });
  }

  render() {
    const {plans, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const planList = plans.map(plan => {
      const address = `${plan.address || ''} ${plan.city || ''} ${plan.county || ''}`;
      return <tr key={plan.id}>
        <td style={{whiteSpace: 'nowrap'}}>{plan.name}</td>
        <td>{address}</td>
        <td>{plan.events.map(event => {
          return <div key={event.id}>{new Intl.DateTimeFormat('en-GB', {
            year: 'numeric',
            month: 'long',
            day: '2-digit'
          }).format(new Date(event.date))}: {event.title}</div>
        })}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="info" tag={Link} to={"/plans/" + plan.id}>Update</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(plan.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/plans/new">Create</Button>
          </div>
          <h3>Address Book</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th>Notes</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {planList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default withCookies(withRouter(PlanList));