import _ from 'lodash';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Communications from 'react-native-communications';
import DeviceForm from './DeviceForm';
import { deviceUpdate, deviceSave, deviceDelete } from '../actions';
import { Card, CardSection, Button, Confirm } from './common';

class DeviceEdit extends Component {
  state = { showModal: false };

  componentWillMount() {
    _.each(this.props.device, (value, prop) => {
      this.props.deviceUpdate({ prop, value });
    });
  }

  onButtonPress() {
    const { name, phone, shift } = this.props;

    this.props.deviceSave({ name, phone, shift, uid: this.props.device.uid });
  }

  onTextPress() {
    const { phone, shift } = this.props;

    Communications.text(phone, `Your upcoming shift is on ${shift}`);
  }

  onAccept() {
    const { uid } = this.props.device;

    this.props.deviceDelete({ uid });
  }

  onDecline() {
    this.setState({ showModal: false });
  }

  render() {
    return (
      <Card>
        <DeviceForm />

        <CardSection>
          <Button onPress={this.onButtonPress.bind(this)}>
            Save Changes
          </Button>
        </CardSection>

        <CardSection>
          <Button onPress={this.onTextPress.bind(this)}>
            Text Schedule
          </Button>
        </CardSection>

        <CardSection>
          <Button onPress={() => this.setState({ showModal: !this.state.showModal })}>
            Fire Employee
          </Button>
        </CardSection>

        <Confirm
          visible={this.state.showModal}
          onAccept={this.onAccept.bind(this)}
          onDecline={this.onDecline.bind(this)}
        >
          Are you sure you want to delete this?
        </Confirm>
      </Card>
    );
  }
}

const mapStateToProps = (state) => {
  const { name, phone, shift } = state.deviceForm;

  return { name, phone, shift };
};

export default connect(mapStateToProps, {
  deviceUpdate, deviceSave, deviceDelete
})(DeviceEdit);
