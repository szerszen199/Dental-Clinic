import React, {Suspense} from "react";
import "./Account.css";
import EditAccount from "../EditAccount/EditAccount";
import EditPassword from "./EditPassword/EditPassword";
import {Col, Container, Row} from "react-bootstrap";
import {withTranslation} from "react-i18next";
import axios from "axios";
import Cookies from "js-cookie";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";

class OwnAccountWithoutTranslation extends React.Component {
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
        let requestPath = process.env.REACT_APP_BACKEND_URL + "account/info"

        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(result => {
                this.setState({
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
                })
            })
            .catch(response => {
                if (response.response) {
                    errorAlerts(t(response.response.data.message), response.response.status.toString(10));
                }
            });
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Edit My Account");
        return (
            <div className="Account">
                <Container>
                    <Row>
                        <Col><EditAccount id={this.state.accId} account={this.state.account} className="EditAccount"/></Col>
                        <Col style={{maxWidth: "60px"}}/>
                        <Col><EditPassword className="EditPassword"/></Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

const OwnAccountTr = withTranslation()(OwnAccountWithoutTranslation)

export default function OwnAccount(props) {
    return (
        <Suspense fallback="loading">
            <OwnAccountTr accId={props.match.params.accId}/>
        </Suspense>
    );
}
