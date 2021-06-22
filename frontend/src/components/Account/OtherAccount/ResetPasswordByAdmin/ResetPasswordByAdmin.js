import React, {Suspense} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./ResetPasswordByAdmin.css";
import {withTranslation} from "react-i18next";
import confirmationAlerts from "../../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import {makeResetPasswordByAdminRequest} from "./ResetPasswordByAdminRequest";

class ResetPasswordByAdminWithoutTranslation extends React.Component {

     handleSubmit(event, title, question, t) {
        return function (event) {
            event.preventDefault();
            confirmationAlerts(title, question).then((confirmed) => {
                if (confirmed) {
                    makeResetPasswordByAdminRequest(this.props.account, t)
                }
            })
        }.bind(this);
    }

    render() {
        const {t} = this.props;

        return (
            <div className="ResetPasswordByAdmin">
                <Form onSubmit={this.handleSubmit(this, t("Warning"), t("Question reset password by admin"), t)}>
                    <Button block size="lg" type="submit">
                        {t("ResetPassword")}
                    </Button>
                </Form>
            </div>
        );
    }
}

const ResetPasswordByAdminTr = withTranslation()(ResetPasswordByAdminWithoutTranslation)

export default function ResetPasswordByAdmin(props) {
    return (
        <Suspense fallback="loading">
            <ResetPasswordByAdminTr account={props.account}/>
        </Suspense>
    );
}
