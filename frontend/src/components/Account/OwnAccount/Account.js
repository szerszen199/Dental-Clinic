import React, {Suspense} from "react";
import "./Account.css";
import EditAccount from "../EditAccount/EditAccount";
import EditPassword from "./EditPassword/EditPassword";
import {Col, Container, Row} from "react-bootstrap";
import {withTranslation} from "react-i18next";
import axios from "axios";
import Cookies from "js-cookie";

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
                version: "",
                etag: "",
            },
        };
    }

    componentDidMount() {
        this.makeGetAccountRequest121();
    }

    makeGetAccountRequest121() {
        console.log(this.state.accId);
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
                        version: result.data.version,
                        etag: result.headers['etag']
                    },
                })
            })
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
