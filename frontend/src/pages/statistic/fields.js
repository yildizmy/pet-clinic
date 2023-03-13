export const petColumns = [
  { field: "id", headerName: "Id", width: 100 },
  {
    field: "name",
    headerName: "Pet Name",
    width: 300,
  },
  {
    field: "type",
    headerName: "Type",
    valueGetter: ({ row }) => row.type.name,
    width: 300,
  },
];
