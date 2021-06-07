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
            version: "",
            accessLevelDtoList: ""
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
            .then(res => {
                return res.data
            })
            .then(result =>
                this.setState({
                    isActivated: result.active,
                    version: result.version,
                    accessLevelDtoList: result.accessLevelDtoList
                })
            )
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
                            <EditAccount className="EditAccount" account={this.state.accId}/>
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
                            <LockUnlockAccount isActive={this.state.isActivated}/>
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }

}
