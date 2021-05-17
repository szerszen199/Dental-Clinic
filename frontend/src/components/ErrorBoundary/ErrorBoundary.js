import React from "react";
import {Button, Popup} from 'semantic-ui-react'

export default class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = {error: null, errorInfo: null};
    }

    componentDidCatch(error, errorInfo) {
        this.setState({
            error: error,
            errorInfo: errorInfo
        })
    }

    static getDerivedStateFromError = error => {
        return {hasError: true};
    };


    render() {
        if (this.state.errorInfo) {
            alert(this.state.errorInfo + "\n" + this.state.error)
            return (
                <span/>
            );
        }
        return this.props.children;
    }
}


