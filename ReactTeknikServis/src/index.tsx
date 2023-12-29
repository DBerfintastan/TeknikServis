import React from "react";
import ReactDOM from "react-dom/client";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Col, Container, Row } from "react-bootstrap";
import MenuComponent from "./components/menu/MenuComponent";
import IndexComponent from "./components/IndexComponent";
import { UserContextProvider } from "./components/context/UsernameContext";
import LoginComponent from "./components/Login/LoginComponent";
import SignUpComponent from "./components/Login/SignUpComponent";
import TakeBookingComponent from "./components/booking/TakeBookingComponent";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <UserContextProvider>
      <BrowserRouter>
        <Container className="pt-3" fluid>
          <Row>
            <Col xs="2">
              <MenuComponent />
            </Col>
            <Col xs="10">
              <Routes>
                {/* {path lere dikkat} */}
                <Route path="/" element={<IndexComponent />} />
                <Route path="/login" element={<LoginComponent />} />
                <Route path="user/save" element={<SignUpComponent />} />
                <Route path="/booking" element={<TakeBookingComponent />} />
              </Routes>
            </Col>
          </Row>
        </Container>
      </BrowserRouter>
    </UserContextProvider>
  </React.StrictMode>
);
