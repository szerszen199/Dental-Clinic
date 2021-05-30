import React from "react";
import "./OtherAccount.css";
import EditAccount from "../EditAccount/EditAccount";
import {Col, Container, FormControl, Row} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {Label} from "semantic-ui-react";
import {useParams} from "react-router-dom";
import GiveRole from "../GiveRole/GiveRole";
import LockUnlockAccount from "../LockUnlockAccount/LockUnlockAccount"
import {makeResetPasswordByAdminRequest} from "./ResetPasswordByAdmin/ResetPasswordByAdminRequest";
import ResetPasswordByAdmin from "./ResetPasswordByAdmin/ResetPasswordByAdmin";

function OtherAccount(props) {
    let {accId} = useParams();
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
                            value={accId}
                        />
                    </Col>
                    <Col style={{maxWidth: "60px"}}/>
                    <Col/>
                </Row>
                <Row>
                    <Col>
                        <EditAccount className="EditAccount" account={accId}/>
                    </Col>
                    <Col style={{maxWidth: "60px"}}/>
                    <Col>
                        <Row>
                            <GiveRole account={accId}/>
                        </Row>
                        <Row>
                            <ResetPasswordByAdmin className="ResetPasswordByAdmin" account={accId}/>
                        </Row>
                        <LockUnlockAccount userLogin={accId}/>
                    </Col>
                </Row>
            </Container>
        </div>
    );

}

export default OtherAccount;
