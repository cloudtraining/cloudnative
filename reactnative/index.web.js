import React, { Component } from 'react';
import { AppRegistry, StyleSheet, Text, TextInput, View, Linking } from 'react-native';
import {Input} from './src/components/common/Input';
import {Button} from './src/components/common/Button';
import axios from 'axios';
class ReactNativeWeb extends Component {
    state = statefulVariable;
    myFunction(){
      axios.get('http:/localhost:8080/UNKNOWN')
          .then(response => this.setState(statefulVariable)
      )
    }

    render() {
        return (
         <div>
           <View style={styles.container}>
             <Text style={styles.welcome}> React Native Web </Text>
             <Text style={styles.instructions}>To get started, edit index.web.js - Press Cmd+R to reload</Text>
           </View>
           <View>
             <Input label='DeviceID:' value='123' onChangeText='xx' placeholder='txt' secureTextEntry={false} />
             <Input label='DeviceName:' value='myDevice' onChangeText='xx' placeholder='txt' secureTextEntry={false} />
             <Input label='DeviceDesc:' value='Description' onChangeText='xx' placeholder='txt' secureTextEntry={false} />
             <Input label='DeviceNumber:' value='333' onChangeText='xx' placeholder='txt' secureTextEntry={false} />
             <Button onPress={() => Linking.openURL("http://localhost:8080/login")  }> Submit </Button>
           </View>
        </div>
    );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
    inputStyle: {
        color: '#000',
        fontSize: 18,
        paddingLeft: 5,
        paddingLeft: 5,
        lineHeight: 23,
        flex: 2
    }
});

AppRegistry.registerComponent('ReactNativeWeb', () => ReactNativeWeb);
AppRegistry.runApplication('ReactNativeWeb', { rootTag: document.getElementById('react-app') });