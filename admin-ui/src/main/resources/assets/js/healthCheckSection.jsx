/* 
 * Renders the health check results table
 * 
 * Depends on:
 * healthCheckConfig.js
 * react.js
 * react-dom.js
 * babel.min.js
 */

class HealthCheckRow extends React.Component {
    constructor(){
        super();

        this.state = {
            currentStatus: "unknown"
        };
    };

    render() {
        return (
        <tr>
            <td><a style={{color: "blue"}} onClick={() => this.refresh(this.props.checkUrl)}>[refresh]</a></td>
            <td>{this.props.checkName}</td>
            <td>{this.props.checkUrl}</td>
            <td>{this.state.currentStatus}</td>
        </tr>
    );
    }  
    
  refresh(url) {
      console.log("Refresh started");
      
      document.body.style.cursor = 'wait';
      fetch(url)
      .then(response => response.json())
      .then(jsonResult => {
        console.log(jsonResult);
        this.setState({
            currentStatus: JSON.stringify(jsonResult)
        });
      })
      .catch( error => {console.log("Error received" + error);
          this.setState({
              currentStatus: "Service Unavailable"
          });
      });
      
      document.body.style.cursor = 'default';
  }
}

class HealthCheckTable extends React.Component {
    constructor(){
        super();

        this.state = {
            healthChecks: []
        };
    };
    
    renderRow(i) {
        //console.log("json full object: %o", this.state.healthChecks);
        //console.log("json full object: %o", Object.keys(this.state.healthChecks));
        //console.log("json full object: %o", Object.keys(this.state.healthChecks.healthChecks[0]));
        return <HealthCheckRow checkName={this.state.healthChecks.healthChecks[i].name} checkUrl={this.state.healthChecks.healthChecks[i].url} />;        
      } 
    
    renderRows() {
        var rows = [];
        
        if (this.state.healthChecks.length == 0) {
            return rows;
        }
        
        for (var i = 0; i < this.state.healthChecks.healthChecks.length; i++) {
            rows.push(this.renderRow(i));
        }
        return rows;
      }
    
    render() {

        return (
                <table>
                    <thead>
                        <tr>
                            <th />
                            <th>Health Check</th>
                            <th>Check Endpoint</th>
                            <th>Current Status</th>
                        </tr>
                    </thead>
                    <tbody>
                            {this.renderRows()}
                    </tbody>
                </table>
        );
      }
    
    componentWillMount() {
        fetch("/admin/json/healthchecks.json")
        .then(response => response.json())
        .then(json => {
          //console.log(json);
          this.setState({
              healthChecks: json
          });
        });
    };
}

/**
 * Note: React component implementations can only return *one* tag.
 */
class HealthCheckSection extends React.Component {
  render() {
    return (
            <div id="healthCheckDiv">
                <h2>Health Check Section</h2>
                <HealthCheckTable />
            </div>
    );
  }
}

function renderHealthSection(divId) {
    ReactDOM.render(
        <HealthCheckSection />,

        /* Dictates "where" this component will be rendered. */ 
        document.getElementById(divId)
      );
}

