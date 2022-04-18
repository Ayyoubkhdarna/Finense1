import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AddTaskExecution from "./components/add-taskExecution.component";
import TaskExecution from "./components/taskExecution.component";
import TasksExecutionsList from "./components/taskExecution-list.component";

class App extends Component {
  render() {
    return (
        <Router>
          <div>
            <nav className="navbar navbar-expand navbar-dark bg-dark">
              <a href="/tasksexecutions" className="navbar-brand">
                AYYOUPI
              </a>
              <div className="navbar-nav mr-auto">
                <li className="nav-item">
                  <Link to={"/tasksexecutions"} className="nav-link">
                    Tasks Executions
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to={"/add"} className="nav-link">
                    Add
                  </Link>
                </li>
              </div>
            </nav>

            <div className="container mt-3">
              <Switch>
                <Route exact path={["/", "/tasksexecutions"]} component={TasksExecutionsList} />
                <Route exact path="/add" component={AddTaskExecution} />
                <Route path="/tasksexecutions/:id" component={TaskExecution} />
              </Switch>
            </div>
          </div>
        </Router>
    );
  }
}

export default App;
