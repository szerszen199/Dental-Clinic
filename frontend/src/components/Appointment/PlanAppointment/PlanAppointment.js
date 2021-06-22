import React from "react";
import "./PlanAppointment.css";
import {Accordion, Button, Card, Col, Container, Row} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import {AppointmentSlot} from "../AppointmentSlot";
import {makeAppointmentSlotsListRequest} from "./AppointmentSlotsListRequest";

class PlanAppointment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            appointmentsList: []
        };
        this.unFilteredList = []
    }

    componentDidMount() {
        this.makeGetAppointmentsSlotsRequest();
    }

    makeGetAppointmentsSlotsRequest() {
        makeAppointmentSlotsListRequest().then((response) => {
            this.unFilteredList = response
            this.setState({appointmentsList: this.unFilteredList})
        })
    }

    renderNull() {
        return <div>{'Loading'}</div>

    }

    renderAppointments(){
        const columns = [
            {
                dataField: 'id',
                text: 'ID',
                style: {verticalAlign: "middle"},
                sort: true,
                hidden: true
            },
            {
                dataField: 'date',
                text: 'Data',
                style: {verticalAlign: "middle"},
                sort: true
            },
            {
                dataField: 'doctor',
                text: 'Lekarz',
                style: {verticalAlign: "middle"},
                sort: true
            }
        ]
        const selectRow = {
            mode: 'radio',
            clickToSelect: true,
            clickToExpand: true,
            hideSelectColumn: true,
            bgColor: '#e6ff99'
        };

        const expandRow = {
            showExpandColumn: false,
            onlyOneExpanding: true,
            renderer: row => (
                <div>
                    <p>{ `Tu moze isc przycisk edycji/wyboru/idk, kolumna  ${row.doctor}` }</p>
                    <p>You can render anything here, also you can add additional data on every row object</p>
                    <p>expandRow.renderer callback will pass the origin row object to you</p>
                </div>
            )
        };

        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.appointmentsList} selectRow={ selectRow }   expandRow={ expandRow } />;
    }

    render() {
        return (
            <div className="MyAppointment">
                <div> Select Appointment</div>
                {!this.state.appointmentsList.length ? this.renderNull() : this.renderAppointments()}
            </div>
        );
    }
}

export default PlanAppointment;
