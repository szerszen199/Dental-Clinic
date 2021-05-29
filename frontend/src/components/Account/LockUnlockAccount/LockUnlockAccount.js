import Button from "react-bootstrap/Button";
import React, {Suspense} from "react";
import {withTranslation} from "react-i18next";
import "./LockUnlockAccount.css"
import {Col, Container, Row} from "react-bootstrap";
import axios from "axios";
import Cookies from "js-cookie";
import successAlerts from "../../Alerts/SuccessAlerts/SuccessAlerts";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import Form from "react-bootstrap/Form";

class LockAccountWithoutTranslation extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isActivated: "",
        };
    }

    componentDidMount() {
        this.makeGetAccountRequest()
    }


    makeGetAccountRequest() {

        console.log(this.props)
        let requestPath = process.env.REACT_APP_BACKEND_URL + "account/other-account-info/" + this.props.userLogin

        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(res => {
                return res.data
            })
            .then(result => this.setState({
                isActivated: result.active
            }))
    }

    renderLockButton(t) {
        return <Button block size="lg" type="submit" onClick={() => {
            this.makeLockAccountRequest(t)
        }}>
            {t("LockAccount")}
        </Button>
    }

    renderUnLockButton(t) {
        return <Button block size="lg" type="submit" onClick={() => {
            this.makeUnlockAccountRequest(t)
        }}>
            {t("UnlockAccount")}
        </Button>
    }


    render() {
        const {t} = this.props;

        return (
            <div className="LockAccount">
                <Row>
                    <Form style={{width: "100%"}}>
                        <Form.Group size="lg" controlId="isLocked">
                            <Form.Label>{t("Active")}</Form.Label>
                                <Form.Control
                                    type="text"
                                    value={this.state.isActivated}
                                    disabled={true}
                                />
                        </Form.Group>
                    </Form>
                </Row>
                <Container id="containerForButtons" style={{minWidth: "108%", marginLeft: "-20px"}}>
                    <Row id="rowForLockButton">
                        <Col>
                            <div className="lockAccountDiv">
                                {this.renderLockButton(t)}
                            </div>
                        </Col>
                        <Col>
                            <div className="UnlockAccountDiv">
                                {this.renderUnLockButton(t)}
                            </div>
                        </Col>
                    </Row>
                </Container>
            </div>
        )
    }

    makeLockAccountRequest(t) {
        let requestPath = process.env.REACT_APP_BACKEND_URL + "account/lock"

        this.makeLockUnlockRequest(requestPath, t);

    }

    makeUnlockAccountRequest(t) {
        let requestPath = process.env.REACT_APP_BACKEND_URL + "account/unlock"

        this.makeLockUnlockRequest(requestPath, t);

    }

    makeLockUnlockRequest(requestPath, t) {
        axios
            .put(requestPath, {
                login: this.props.userLogin
            }, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            }).then((response) => {
            successAlerts(t(response.data.message, response.status)).then(() => {
                this.makeGetAccountRequest()
            })
        })
            .catch((response) => {
                if (response.response) {
                    errorAlerts(t(response.response.data.message), response.response.status.toString(10));
                }
            });
    }
}

const LockAccountTr = withTranslation()(LockAccountWithoutTranslation)

export default function LockUnlockAccount(props) {
    return (
        <Suspense fallback="loading">
            <LockAccountTr userLogin={props.userLogin}/>
        </Suspense>
    );
}