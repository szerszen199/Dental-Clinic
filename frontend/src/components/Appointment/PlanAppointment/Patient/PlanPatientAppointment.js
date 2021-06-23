import React, {Suspense} from "react";
import "../PlanAppointment.css";
import BootstrapTable from "react-bootstrap-table-next";
import {makeAppointmentSlotsListRequest} from "../AppointmentSlotsListRequest";
import {withTranslation} from "react-i18next";
import {makePatientsListRequest} from "../../ListPatients/ListPatientsRequest";
import {Button} from "react-bootstrap";
import {FiRefreshCw} from "react-icons/fi";
import {planPatientAppointmentRequest} from "./PlanPatientAppointmentRequest";
import confirmationAlerts from "../../../Alerts/ConfirmationAlerts/ConfirmationAlerts";

class PlanPatientAppointmentWithoutTr extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            patientsList: [],
            appointmentsList: []
        };
        this.unFilteredList = []
    }

    componentDidMount() {
        this.makeGetPatientRequest();
        this.makeGetAppointmentsSlotsRequest();
    }

    makeGetPatientRequest() {
        makePatientsListRequest().then((response) => {
            this.unFilteredList = response
            this.setState({patientsList: this.unFilteredList})
        })
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

    setChosenAccount(rowIndex) {
        this.setState({chosenAccount: this.state.patientsList[rowIndex]})
    }

    handleSubmit(appointmentId, t) {

            confirmationAlerts(t('title_reserve_appointment'), t('reserve_appointment_text')).then((confirmed) => {
                if (confirmed) {
                    planPatientAppointmentRequest(appointmentId, t);
                }
            })

    }

    renderButton() {
        return <Button variant={"secondary"} size="lg" onClick={() => {
            this.makeGetAccountRequest()
        }}>
            <FiRefreshCw/>
        </Button>
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
            bgColor: '#e0f4fc'
        };

        const expandRow = {
            showExpandColumn: false,
            onlyOneExpanding: true,
            renderer: row => (
                <div>
                    <form  onSubmit={()=>this.handleSubmit(row.id,t)}>
                        <Button size={"lg"} type="submit">
                            book
                        </Button>
                    </form>
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

const AppointmentWithTranslation =  withTranslation()(PlanPatientAppointmentWithoutTr);

export default function PlanReceptionistAppointment(){
    return (
        <Suspense fallback="loading">
            <AppointmentWithTranslation/>
        </Suspense>
    );
}
