import React, {Suspense} from "react";

import {Button, Col, Container, FormControl, Row} from "react-bootstrap";
import {Label} from "semantic-ui-react";

import axios from "axios";
import Cookies from "js-cookie";
import Form from "react-bootstrap/Form";
import {withTranslation} from "react-i18next";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";
import {DocumentationEntry} from "./DocumentationEntry";
import BootstrapTable from "react-bootstrap-table-next";
import {Link} from "react-router-dom";
import edit from "../../assets/edit.png";
import * as PropTypes from "prop-types";
import {Fragment} from "react";
import {FiRefreshCw} from "react-icons/fi";
import confirmationAlerts from "../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import successAlerts from "../Alerts/SuccessAlerts/SuccessAlerts";


class DocumentationListWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accId: this.props.accId,
            documentation: [1]
        };
    }

    componentDidMount() {
        this.makeGetAllDocumentationRequest();
    }

    renderButton() {
        let self = this;
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetAllDocumentationRequest()
        }}>
            <FiRefreshCw/>
        </Button>
    }


    makeGetAllDocumentationRequest() {
        const {t} = this.props;
        let self = this;
        axios.post(process.env.REACT_APP_BACKEND_URL + "documentation/get-all", {
            patient: this.state.accId,
        }, {
            headers: {
                Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
            }
        }).then(function (result) {
            const allEntries = [];
            for (const documentationEntry of result.data.documentationEntries) {
                allEntries.push(new DocumentationEntry(
                    documentationEntry.creationTime,
                    documentationEntry.modificationTime,
                    documentationEntry.wasDone,
                    documentationEntry.toBeDone,
                    documentationEntry.id,
                    documentationEntry.version,
                    documentationEntry.doctorLogin,
                    documentationEntry.etag));
            }
            let compare = function (a, b) {
                if (a.creationTime < b.creationTime) {
                    return -1;
                }
                if (a.creationTime > b.creationTime) {
                    return 1;
                }
                return 0;
            }
            allEntries.sort(compare);
            self.setState({
                accId: result.data.patientUsername,
                documentation: allEntries
            })
        }).catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
    }

    makeDeleteDocumentationRequest(id) {
        const {t} = this.props;
        let self = this;
        confirmationAlerts(t("Warning"), t("delete_documentation_entry")).then((confirmed) => {
            if (confirmed) {
                axios.post(process.env.REACT_APP_BACKEND_URL + "documentation/delete", {
                    id: id,
                }, {
                    headers: {
                        Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                    }
                }).then((res) => {
                    successAlerts(t(res.data.message, res.status)).then(() => {
                        self.makeGetAllDocumentationRequest();
                    })
                }).catch((res) => {
                    if (res.response) {
                        errorAlerts(t(res.response.data.message), res.response.status.toString(10));
                    }
                });
            }
        });
    }

    linkEdit = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Button variant="outline-secondary">
                <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}
                     onClick={() => {
                         this.makeDeleteDocumentationRequest(this.state.documentation[rowIndex].id)
                     }}/>
            </Button>
        );
    }

    renderDocumentation() {
        const {t} = this.props;
        const columns = [
            {
                dataField: 'creationTime',
                text: t('creation_time'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'modificationTime',
                text: t('modification_time'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'wasDone',
                text: t('was_done'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'toBeDone',
                text: t('to_be_done'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'doctorLogin',
                text: t('doctorLogin'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'actions',
                text: t('delete'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkEdit
            }
        ]

        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.documentation}/>;
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Documentation");
        return (
            <Fragment>
                <div className="account-refresh-button-div">
                    {this.renderButton()}
                </div>
                <div className="documentation">
                    <Container>
                        {this.renderDocumentation()}
                    </Container>
                </div>
            </Fragment>
        );
    }

}

const DocumentationListTr = withTranslation()(DocumentationListWithoutTranslation)

export default function DocumentationList(props) {
    return (
        <Suspense fallback="loading">
            <DocumentationListTr accId={props.match.params.accId}/>
        </Suspense>
    );
}
