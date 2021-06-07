import React from "react";
import "./OtherAccount.css";
import EditAccount from "../EditAccount/EditAccount";
import {Col, Container, FormControl, Row} from "react-bootstrap";
import {Label} from "semantic-ui-react";
import GiveRole from "../Role/GiveRole";
import LockUnlockAccount from "../LockUnlockAccount/LockUnlockAccount"
import ResetPasswordByAdmin from "./ResetPasswordByAdmin/ResetPasswordByAdmin";
import axios from "axios";
import Cookies from "js-cookie";

export default class OtherAccount extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accId: this.props.match.params.accId,
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
        let requestPath = process.env.REACT_APP_BACKEND_URL + "account/other-account-info/" + this.state.accId;

        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(result => {
                this.setState({
                    isActivated: result.data.active,
                    accessLevelDtoList: result.data.accessLevelDtoList,
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
                            <Row>
                                <ResetPasswordByAdmin className="ResetPasswordByAdmin" account={this.state.accId}/>
                            </Row>
                            <LockUnlockAccount login={this.state.accId} isActive={this.state.isActivated}/>
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }

}
