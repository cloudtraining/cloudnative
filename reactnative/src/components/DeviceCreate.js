import React, { Component } from 'react';
import { connect } from 'react-redux';
import { deviceUpdate, deviceCreate } from '../actions';
import { Card, CardSection, Button } from './common';
import DeviceForm from './DeviceForm';

class DeviceCreate extends Component {
  onButtonPress() {
    const { name, phone, shift } = this.props;

    this.props.deviceCreate({ name, phone, shift: shift || 'Monday' });
  }

  render() {
    return (
      <Card>
        <DeviceForm {...this.props} />
        <CardSection>
          <Button onPress={this.onButtonPress.bind(this)}>
            Create
          </Button>
        </CardSection>
      </Card>
    );
  }
}

const mapStateToProps = (state) => {
  const { name, phone, shift } = state.deviceForm;

  return { name, phone, shift };
};

export default connect(mapStateToProps, {
  deviceUpdate, deviceCreate
})(DeviceCreate);
