import { DataGrid } from "@mui/x-data-grid";
import { Link } from "react-router-dom";
import "./datatable.scss";

const Datatable = ({ title, userColumns, actionColumn, data, to }) => {
  return (
    <div className="datatable">
      <div className="datatableTitle">
        <h1 className="title">{title}</h1>
        <Link to={to} className="link">
          Add New
        </Link>
      </div>
      <DataGrid
        className="datagrid"
        rows={data}
        columns={userColumns.concat(actionColumn)}
        pageSize={9}
        rowsPerPageOptions={[9]}
        // checkboxSelection
      />
    </div>
  );
};

export default Datatable;
