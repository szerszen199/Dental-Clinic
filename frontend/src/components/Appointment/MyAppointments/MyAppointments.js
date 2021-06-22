import React, {Fragment, Suspense} from "react";
import "./MyAppointments.css";
import {Button, Container} from "react-bootstrap";
import {withTranslation} from "react-i18next";
import {makeMyAppointmentsListRequest} from "./MyAppointmentsRequest";
import {FiRefreshCw} from "react-icons/fi";
import edit from "../../../assets/edit.png";
import BootstrapTable from "react-bootstrap-table-next";

class MyAppointmentsWithoutTranslation extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            appointments: [],
        };
    }

    componentDidMount() {
        this.makeGetAppointments();
    }

    makeGetAppointments() {
        let self = this;
        makeMyAppointmentsListRequest(this.props).then((response) => {
            self.setState({appointments: response});
        })
    }

    renderButton() {
        let self = this;
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetAppointments()
        }}>
            <FiRefreshCw/>
        </Button>
    }


    linkEdit = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Button variant="outline-secondary">
                <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}
                    // onClick={this.edit(this.state.documentation[rowIndex].id)}
                />
            </Button>
        );
    }

    renderTableOfAppointments() {
        const {t} = this.props;
        const columns = [
            {
                dataField: 'doctorName',
                text: t('doctor_name'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'patientName',
                text: t('patient_name'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'date',
                text: t('date'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'actions',
                text: t('edit'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkEdit
            }
        ]

        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.appointments}/>;
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("scheduled_appointments");
        return (
            <Fragment>
                <div className="account-refresh-button-div">
                    {this.renderButton()}
                </div>
                <div className="documentation">
                    <Container>
                        {this.renderTableOfAppointments()}
                    </Container>
                </div>
            </Fragment>
        );
    }
}


const MyAppointmentsTr = withTranslation()(MyAppointmentsWithoutTranslation)

export default function MyAppointments() {
    return (
        <Suspense fallback="loading">
            <MyAppointmentsTr/>
        </Suspense>
    );
}
