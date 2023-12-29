import { Button, Stack } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import { UserContext } from "../context/UsernameContext";
import { useContext } from "react";

export default function MenuComponent() {
  const mynavigate = useNavigate();
  const ourUserContext = useContext(UserContext);

  return (
    <Stack>
      <h1>Teknik Servis</h1>
      <Stack gap={2}>
        {/* react-router-dom 'dan gelen navlik olacak */}
        <NavLink to="/">Anasayfa</NavLink>
        <NavLink
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          to="/booking"
          end
        >
          Bookings
        </NavLink>
        {/* context içerisindeki state bu componentin render edilmesini sağlıyor */}
        <NavLink
          to="/ogrenci"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          end
        >
          Proposals
        </NavLink>
        <NavLink
          to="/ogrenci/kaydet"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
        >
          Sales
        </NavLink>
        <NavLink
          to="/konu"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          end
        >
          Services
        </NavLink>

        <NavLink
          to="/konu/kaydet"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
        >
          Products
        </NavLink>
        <NavLink
          to="/ders"
          end
          className={({ isActive }) => (isActive ? "bg-info" : "")}
        >
          Sale_Log
        </NavLink>
        {localStorage.getItem("userjwt") === null && (
          <NavLink
            to="user/save"
            className={({ isActive }) => (isActive ? "bg-info" : "")}
          >
            User sign up
          </NavLink>
        )}

        {localStorage.getItem("userjwt") === null && (
          <NavLink
            to="/login"
            className={({ isActive }) => (isActive ? "bg-info" : "")}
          >
            Login
          </NavLink>
        )}
        {localStorage.getItem("userjwt") !== null && (
          <Button
            variant="outline-danger"
            onClick={() => {
              localStorage.removeItem("username");
              localStorage.removeItem("userjwt");
              localStorage.removeItem("authorities");
              ourUserContext.setterforusername("");
              // replace çalışmadı
              mynavigate("/", { replace: true });
            }}
          >
            Logout
          </Button>
        )}
      </Stack>
    </Stack>
  );
}
