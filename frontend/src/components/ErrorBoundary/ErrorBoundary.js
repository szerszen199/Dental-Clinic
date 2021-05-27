import React from "react";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";

export default class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = {error: null, errorInfo: null};
    }

    static getDerivedStateFromError = error => {
        return {hasError: true};
    };

    componentDidCatch(error, errorInfo) {
        this.setState({
            error: error,
            errorInfo: errorInfo
        })
    }

    render() {
        if (this.state.errorInfo) {
            errorAlerts(this.state.errorInfo, this.state.error);
            return (
                <span/>
            );
        }
        return this.props.children;
    }
}


