import { useEffect, useState } from 'react';
import api from '../api/client';

export default function DashboardPage() {
  const [metrics, setMetrics] = useState({ leads: 0, bookings: 0, revenue: 0 });

  useEffect(() => {
    Promise.all([api.get('/leads?page=1&pageSize=1'), api.get('/bookings?page=1&pageSize=100'), api.get('/payments?page=1&pageSize=500')])
      .then(([leads, bookings, payments]) => {
        const revenue = payments.data.items.reduce((sum, p) => sum + p.amount, 0);
        setMetrics({ leads: leads.data.total, bookings: bookings.data.total, revenue });
      });
  }, []);

  return (
    <>
      <div className="card"><div>Leads</div><div className="metric">{metrics.leads}</div></div>
      <div className="card"><div>Bookings</div><div className="metric">{metrics.bookings}</div></div>
      <div className="card"><div>Revenue</div><div className="metric">₹ {metrics.revenue}</div></div>
    </>
  );
}
