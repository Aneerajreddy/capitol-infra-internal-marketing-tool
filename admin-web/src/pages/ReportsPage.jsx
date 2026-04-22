import { useEffect, useState } from 'react';
import api from '../api/client';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from 'recharts';

export default function ReportsPage() {
  const [data, setData] = useState([]);
  useEffect(() => {
    api.get('/payments?page=1&pageSize=1000').then(({ data }) => {
      const grouped = data.items.reduce((acc, p) => {
        const day = new Date(p.paidAt).toISOString().slice(0, 10);
        acc[day] = (acc[day] || 0) + p.amount;
        return acc;
      }, {});
      setData(Object.entries(grouped).map(([day, revenue]) => ({ day, revenue })));
    });
  }, []);

  return <div className="card" style={{ height: 300 }}><ResponsiveContainer><BarChart data={data}><XAxis dataKey="day"/><YAxis/><Tooltip/><Bar dataKey="revenue" fill="#2563eb"/></BarChart></ResponsiveContainer></div>;
}
