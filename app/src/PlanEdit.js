import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { instanceOf } from 'prop-types';
import { Cookies, withCookies } from 'react-cookie';

class PlanEdit extends Component {
	static propTypes = {
	    cookies: instanceOf(Cookies).isRequired
	  };

  emptyItem = {
    name: '',
    address: '',
    city: '',
    county: '',
    country: '',
    postCode: '',
    notes: ''
  };

  constructor(props) {
	    super(props);
	    const {cookies} = props;
	    this.state = {
	      item: this.emptyItem,
	      csrfToken: cookies.get('XSRF-TOKEN')
	    };
	    this.handleChange = this.handleChange.bind(this);
	    this.handleSubmit = this.handleSubmit.bind(this);
	  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
     try {
       const plan = await (await fetch(`/api/plan/${this.props.match.params.id}`)).json();
       this.setState({item: plan});
     } catch (error) {
       this.props.history.push('/');
     }
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item, csrfToken} = this.state;

    await fetch('/api/plan', {
      method: (item.id) ? 'POST' : 'POST',
      headers: {
    	'X-XSRF-TOKEN': this.state.csrfToken,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/plans');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Contact' : 'Add Contact'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
                   onChange={this.handleChange}/>
          </FormGroup>
	      <FormGroup>
	        <Label for="name">Phone Number</Label>
	        <Input type="text" name="phone" id="phone" value={item.phone || ''}
	               onChange={this.handleChange}/>
	      </FormGroup>
          <FormGroup>
            <Label for="address">Address</Label>
            <Input type="text" name="address" id="address" value={item.address || ''}
                   onChange={this.handleChange}/>
          </FormGroup>
          <FormGroup>
            <Label for="city">City</Label>
            <Input type="text" name="city" id="city" value={item.city || ''}
                   onChange={this.handleChange}/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="county">County</Label>
              <Input type="text" name="county" id="county" value={item.county || ''}
                     onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="country">Country</Label>
              <Input type="text" name="country" id="country" value={item.country || ''}
                     onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="country">Post Code</Label>
              <Input type="text" name="postCode" id="postCode" value={item.postCode || ''}
                     onChange={this.handleChange}/>
            </FormGroup>
          </div>
	        <FormGroup>
	          <Label for="notes">Notes</Label>
	          <Input type="text" name="notes" id="notes" value={item.notes || ''}
	                 onChange={this.handleChange}/>
	        </FormGroup>          
          <FormGroup>
            <Button color="success" type="submit">Save</Button>{' '}
            <Button color="danger" tag={Link} to="/plans">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withCookies(withRouter(PlanEdit));