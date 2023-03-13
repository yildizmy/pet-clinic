import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import "./auth.scss";

const Unauthorized = () => {
  const pageTitle = "Anauthorized";

  return (
    <div className="single">
      <Sidebar />
      <div className="singleContainer">
        <Navbar />
        <div className="bottom">
          <h1 className="title">{pageTitle}</h1>
          <p>You are not authorized to view this page</p>
        </div>
      </div>
    </div>
  );
};

export default Unauthorized;
