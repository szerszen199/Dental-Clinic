import React from 'react';


// TODO: Można użyć tego do tego czy mamy połączenie do strony czytaj jesli tutaj się zwróci "ready" i okejka (200) to jest git
export default class ReadinessComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            ready: ""
        }
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_BACKEND_URL + "readiness")
            .then(res => res.json())
            .then(result => this.setState({ready: result.message}))
    }

    render() {
        console.log(this.state.ready)
        return (
            <span/>
        )
    }
}
