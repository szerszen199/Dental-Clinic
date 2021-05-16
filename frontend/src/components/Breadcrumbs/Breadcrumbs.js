import React from "react";
import {useLocation, useHistory, Link} from "react-router-dom";
import {Breadcrumbs} from "@material-ui/core";

import "./Breadcrumbs.css";

function BreadCrumbs() {
    return (
        <Breadcrumbs aria-label="breadcrumb" className="text-muted">
            <HeaderView/>
        </Breadcrumbs>
    )
}

function HeaderView() {
    const str = useLocation().pathname.substring(1).split('/');
    let rows = [];
    const history = useHistory();
    for (let i = 0; i < str.length; i++) {
        let url_str = [];
        for (let j = 0; j <= i; j++) {
            url_str.push("/");
            url_str.push(str[j]);
        }
        const handleOnClick = () => history.push(url_str.join(""));
        rows.push(
            <span>
                {" "} / {" "}
                <Link href="#" onClick={handleOnClick}>
                    {str[i]}
                </Link>
            </span>
        )
    }
    return <span>{rows}</span>
}

export default BreadCrumbs;