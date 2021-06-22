import React, {Suspense} from "react";
import "../PlanAppointment.css";
import BootstrapTable from "react-bootstrap-table-next";
import {makeAppointmentSlotsListRequest} from "../AppointmentSlotsListRequest";
import {withTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import DeleteIcon from "@material-ui/icons/Delete";
import EditIcon from '@material-ui/icons/Edit';
import Button from "@material-ui/core/Button";
import {ButtonGroup, IconButton} from "@material-ui/core";
import {deleteAppointmentSlotRequest} from "../DeleteAppointmentSlotRequest";
import confirmationAlerts from "../../../Alerts/ConfirmationAlerts/ConfirmationAlerts";

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

    renderAppointments() {
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
                    <ButtonGroup style={{marginLeft: 10}}>
                        <Button
                            size={"small"}
                            variant="contained"
                            startIcon={<EditIcon/>}>
                            <Link to={"/appointment-slot/" + row.id}>
                                {t("Edit")}
                            </Link>
                        </Button>
                        <Button
                            size={"small"}
                            variant="contained"
                            color="secondary"
                            startIcon={<DeleteIcon/>}
                            onClick={() => {
                                this.handleDeleteButtonClick(row.id, t('Warning'), t('Question delete appointment slot'), t)
                            }}>
                            {t('delete')}
                        </Button>
                    </ButtonGroup>
                </div>
            )
        };

        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.appointmentsList}
                               selectRow={selectRow} expandRow={expandRow}/>;
    }

    handleDeleteButtonClick(id, title, question, t) {
            confirmationAlerts(title, question).then((confirmed) => {
                if (confirmed) {
                    deleteAppointmentSlotRequest(id, t);
                }
            });
    }

    render() {
        return (
            <div className="MyAppointment">
                {!this.state.appointmentsList.length ? this.renderNull() : this.renderAppointments()}
            </div>
        );
    }
}

const AppointmentWithTranslation = withTranslation()(PlanReceptionistAppointmentWithoutTr);

export default function PlanReceptionistAppointment() {
    return (
        <Suspense fallback="loading">
            <AppointmentWithTranslation/>
        </Suspense>
    );
}
