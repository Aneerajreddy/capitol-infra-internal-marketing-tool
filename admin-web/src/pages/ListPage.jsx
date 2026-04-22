import { useEffect, useState } from 'react';
import api from '../api/client';

export default function ListPage({ resource, columns }) {
  const [rows, setRows] = useState([]);

  useEffect(() => {
    api.get(`/${resource}?page=1&pageSize=50`).then(({ data }) => setRows(data.items));
  }, [resource]);

  return (
    <div className="card">
      <h3>{resource.toUpperCase()}</h3>
      <table className="table">
        <thead><tr>{columns.map(c => <th key={c}>{c}</th>)}</tr></thead>
        <tbody>
          {rows.map((r) => (
            <tr key={r._id}>{columns.map((c) => <td key={c}>{String(r[c] ?? '')}</td>)}</tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
