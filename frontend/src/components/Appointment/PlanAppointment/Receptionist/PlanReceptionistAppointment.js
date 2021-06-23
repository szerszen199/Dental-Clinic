import React, {Suspense} from "react";
import "../PlanAppointment.css";
import BootstrapTable from "react-bootstrap-table-next";
import {makeAppointmentSlotsListRequest} from "../AppointmentSlotsListRequest";
import {withTranslation} from "react-i18next";
import {Link} from "react-router-dom";

class PlanReceptionistAppointmentWithoutTr extends React.Component {
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
        const {t} = this.props;
        return <div>{t('Loading')}</div>

    }

    renderAppointments(){
        const {t} = this.props;
        const columns = [
            {
                dataField: 'id',
                text: t('ID'),
                style: {verticalAlign: "middle"},
                sort: true,
                hidden: true
            },
            {
                dataField: 'date',
                text: t('Date'),
                style: {verticalAlign: "middle"},
                sort: true
            },
            {
                dataField: 'doctor.lastName',
                text: t('Doctors Last Name'),
                style: {verticalAlign: "middle"},
                sort: true
            },
            {
                dataField: 'doctor.firstName',
                text: t('Doctors First Name'),
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
                    <Link to={"/appointment-slot/" + row.id}>
                        {t("Edit")}
                    </Link>
                    <p>expandRow.renderer callback will pass the origin row object to you</p>
                </div>
            )
        };

        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.appointmentsList} selectRow={ selectRow }   expandRow={ expandRow } />;
    }

    render() {
        return (
            <div className="MyAppointment">
                {!this.state.appointmentsList.length ? this.renderNull() : this.renderAppointments()}
            </div>
        );
    }
}

const AppointmentWithTranslation =  withTranslation()(PlanReceptionistAppointmentWithoutTr);

export default function PlanReceptionistAppointment(){
    return (
        <Suspense fallback="loading">
            <AppointmentWithTranslation/>
        </Suspense>
    );
}
