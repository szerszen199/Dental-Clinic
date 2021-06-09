import React, {Suspense} from "react";
import Nav from "react-bootstrap/Nav";
import {ButtonGroup} from "@material-ui/core";
import Button from "react-bootstrap/Button";
import Cookies from "js-cookie";
import {giveRoleRequest} from "./GiveRoleRequest";
import {removeRoleRequest} from "./RemoveRoleRequest";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import {withTranslation} from "react-i18next";
import axios from "axios";

class GiveRoleWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            rolesList: []
        };
    }

    componentDidMount() {
        this.setState({
            rolesList: this.props.accessLevelDtoList
        })
    }

    componentDidUpdate(prevProps) {
        if (prevProps.accessLevelDtoList !== this.props.accessLevelDtoList) {
            this.setState({
                rolesList: this.props.accessLevelDtoList
            });
            this.render();
        }

    }

    makeAccessLevelRequest() {
        axios.get(process.env.REACT_APP_BACKEND_URL + "account/other-account-info/" + this.props.account, {
            headers: {
                Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
            }
        }).then(result => {
            this.setState({
                rolesList: result.data.accessLevelDtoList
            })
        });
    }

    giveRoleRequestAndRefresh(login, level) {
        const {t} = this.props;
        confirmationAlerts(t("Warning"), t("Activate role")).then((confirmed) => {
            if (confirmed) {
                giveRoleRequest(login, level, () => {
                    this.makeAccessLevelRequest()
                }, () => {
                    this.render()
                }, t);
            }
        });
    }

    takeRoleRequestAndRefresh(login, level) {
        const {t} = this.props;
        confirmationAlerts(t("Warning"), t("Deactivate role")).then((confirmed) => {
            if (confirmed) {
                removeRoleRequest(login, level, () => {
                    this.makeAccessLevelRequest()
                }, () => {
                    this.render()
                }, t);
            }
        });
    }

    buttonsFunc() {
        let allButtons = [];
        for (let i = 0; i < this.state.rolesList.length; i++) {
            if (this.state.rolesList[i].active === true) {
                allButtons.push(<Button variant="primary" onClick={() => {
                    this.takeRoleRequestAndRefresh(this.props.account, this.state.rolesList[i].level)
                }} type="submit">{this.state.rolesList[i].level}</Button>);
            } else {
                allButtons.push(<Button variant="secondary" onClick={() => {
                    this.giveRoleRequestAndRefresh(this.props.account, this.state.rolesList[i].level)
                }} type="submit">{this.state.rolesList[i].level}</Button>);
            }
        }
        return allButtons
    }

    render() {
        const {t} = this.props;
        let Buttons;
        // if (this.state.rolesList && this.state.rolesList.length !== 0) {
        Buttons = <ButtonGroup>
            {this.buttonsFunc()}
        </ButtonGroup>
        // } else {
        //     Buttons = "Trwa wczytywanie";
        // }

        return (
            <Nav>
                {t("Activate/Deactivate role")}
                {Buttons}
            </Nav>
        );
    }
}

const GiveRoleTr = withTranslation()(GiveRoleWithoutTranslation)

export default function GiveRole(props) {
    return (
        <Suspense fallback="loading">
            <GiveRoleTr accessLevelDtoList={props.accessLevelDtoList} account={props.account}/>
        </Suspense>
    );
}





