import React, {Fragment, Suspense} from "react";
import "../PlanAppointment.css";
import BootstrapTable from "react-bootstrap-table-next";
import {makeAppointmentSlotsListRequest} from "../AppointmentSlotsListRequest";
import {withTranslation} from "react-i18next";
import {makePatientsListRequest} from "../../ListPatients/ListPatientsRequest";
import {planAppointmentRequest} from "./PlanAppointmentRequest";
import {Button, Col, Row} from "react-bootstrap";
import {FiRefreshCw} from "react-icons/fi";
import Cookies from "js-cookie";
import {Input} from "semantic-ui-react";

class PlanReceptionistAppointmentWithoutTr extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            patientsList: [],
            appointmentsList: [],
            chosenAccount: ""
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

    getHintList(list) {
        let tempList = [];
        let uniqueList = [...new Set(this.state.patientsList.map(a => a.name))];
        for (let i in uniqueList) {
            tempList.push(<option key={i} value={uniqueList[i]}>{uniqueList[i]}</option>);
        }
        return tempList;

    }

    filterList(input) {
        let tempList = []
        for (let i in this.unFilteredList) {
            if (this.unFilteredList[i].name.toUpperCase().includes(input.toUpperCase())) {
                tempList.push(this.unFilteredList[i])
            }
        }
        this.setState({patientsList: tempList})
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

    renderAccounts() {
        const {t} = this.props;
        const selectRow = {
            mode: 'radio',
            clickToSelect: true,
            hideSelectColumn: true,
            bgColor: '#e6ff99',
            onSelect:(var1,var2,rowIndex)=>{
                this.setState({chosenAccount: this.state.patientsList[rowIndex]});
            },
        };

        const columns = [

            {
                dataField: 'login',
                text: t('UserLogin'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'name',
                text: t('Name and Surname'),
                style: {verticalAlign: "middle"}
            }
        ]
        return <BootstrapTable striped keyField='login' columns={columns} data={this.state.patientsList} selectRow={ selectRow }/>;
    }

    setChosenAccount(rowIndex) {
        this.setState({chosenAccount: this.state.patientsList[rowIndex]})
    }

    handleSubmit(appointmentId,t) {
        planAppointmentRequest(appointmentId, this.state.chosenAccount,t);
    }

    renderButton() {
        return <Button variant={"secondary"} size="lg" onClick={() => {
            this.makeGetAccountRequest()
        }}>
            <FiRefreshCw/>
        </Button>
    }

    renderPatientList(t) {
        let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

        function isPatient() {
            return token !== undefined && Cookies !== undefined && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_PATIENT;
        }
        if (!isPatient()) {
            return<Col>
                <Fragment>
                    <datalist id='options'>
                        {this.state.patientsList.length !== this.unFilteredList.length ? this.getHintList() : []}
                    </datalist>
                    <div className="AccountListGroup">
                        <Input list='options' id="ListFilter"
                               onChange={e => this.filterList(e.target.value)}
                               placeholder={t("Filter")}/>
                        {!this.state.patientsList.length ? this.renderNull() : this.renderAccounts()}
                    </div>
                </Fragment>
            </Col>
        }
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
        const {t} = this.props;
        return (
            <div className="MyAppointment">
                <Row>
                    <Col>
                {!this.state.appointmentsList.length ? this.renderNull() : this.renderAppointments()}
                    </Col>
                    <Col>
                {this.renderPatientList(t)}
                    </Col>
                </Row>
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
