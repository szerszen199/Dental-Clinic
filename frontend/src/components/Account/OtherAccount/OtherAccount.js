import React, {Suspense} from "react";
import "./OtherAccount.css";
import EditAccount from "../EditAccount/EditAccount";
import {Col, Container, FormControl, Row} from "react-bootstrap";
import {Label} from "semantic-ui-react";
import GiveRole from "../Role/GiveRole";
import LockUnlockAccount from "../LockUnlockAccount/LockUnlockAccount"
import ResetPasswordByAdmin from "./ResetPasswordByAdmin/ResetPasswordByAdmin";
import axios from "axios";
import Cookies from "js-cookie";
import Form from "react-bootstrap/Form";
import {withTranslation} from "react-i18next";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";

class OtherAccountWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accId: this.props.accId,
            isActivated: "",
            accessLevelDtoList: "",
            account: {
                email: "",
                firstName: "",
                lastName: "",
                phoneNumber: "",
                pesel: "",
                lastSuccessfulLogin: "",
                lastSuccessfulLoginIp: "",
                lastUnsuccessfulLogin: "",
                lastUnsuccessfulLoginIp: "",
                version: "",
                etag: "",
            },
        };
    }

    componentDidMount() {
        this.makeGetAccountRequest121();
    }

    makeGetAccountRequest121() {
        const {t} = this.props;
        let requestPath = process.env.REACT_APP_BACKEND_URL + "account/other-account-info/" + this.state.accId;

        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(result => {
                this.setState({
                    accId: result.data.login,
                    isActivated: result.data.active,
                    accessLevelDtoList: result.data.accessLevelDtoList,
                    account: {
                        email: result.data.email,
                        firstName: result.data.firstName,
                        lastName: result.data.lastName,
                        phoneNumber: result.data.phoneNumber,
                        pesel: result.data.pesel,
                        lastSuccessfulLogin: result.data.lastSuccessfulLogin,
                        lastSuccessfulLoginIp: result.data.lastSuccessfulLoginIp,
                        lastUnsuccessfulLogin: result.data.lastUnsuccessfulLogin,
                        lastUnsuccessfulLoginIp: result.data.lastUnsuccessfulLoginIp,
                        version: result.data.version,
                        etag: result.headers['etag']
                    },
                    enabled: result.data.enabled,
                })
                console.log("result: " + result);
            }).catch((response) => {
                console.log(response);
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10)).then(() => {
                    window.location.hash = "#/accounts";
                });
            }
        })
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Edit Other Account");
        return (
            <div className="OtherAccount">
                <Container>
                    <Row>
                        <Col style={{maxWidth: "515px"}}>
                            <Label className={"LoginLabel required"}>
                                Login
                            </Label>
                            <FormControl
                                type="text"
                                disabled={true}
                                value={this.state.accId}
                            />
                        </Col>
                        <Col style={{maxWidth: "60px"}}/>
                        <Col/>
                    </Row>
                    <Row>
                        <Col>
                            <EditAccount className="EditAccount" id={this.state.accId} account={this.state.account}/>
                        </Col>
                        <Col style={{maxWidth: "60px"}}/>
                        <Col>
                            <Row>
                                <GiveRole accessLevelDtoList={this.state.accessLevelDtoList}
                                          account={this.state.accId}/>
                            </Row>
                            <hr/>
                            <Row>
                                <ResetPasswordByAdmin className="ResetPasswordByAdmin" account={this.state.accId}/>
                            </Row>
                            <hr/>
                            <LockUnlockAccount login={this.state.accId} isActive={this.state.isActivated}/>
                            <hr/>
                            <div className="enabled">
                                <Row>
                                    <Form style={{width: "100%"}}>
                                        <Form.Group size="lg" controlId="isEnabled">
                                            <Form.Label>{t("Enabled")}</Form.Label>
                                            <Form.Control
                                                type="text"
                                                value={this.state.enabled}
                                                disabled={true}
                                            />
                                        </Form.Group>
                                    </Form>
                                </Row>
                            </div>
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }

}

const OtherAccountTr = withTranslation()(OtherAccountWithoutTranslation)

export default function OtherAccount(props) {
    return (
        <Suspense fallback="loading">
            <OtherAccountTr accId={props.match.params.accId}/>
        </Suspense>
    );
}
