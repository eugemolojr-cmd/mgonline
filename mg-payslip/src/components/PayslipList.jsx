import { useEffect, useState } from 'react';
import { payslipService } from '../services/payslipService';

const peso = (n) => Number(n).toLocaleString('en-PH', { minimumFractionDigits: 2 });

export default function PayslipList() {
  const [payslips, setPayslips] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    payslipService.listMine().then(setPayslips).catch((e) => setError(e.message));
  }, []);

  if (error) return <p style={{ color: 'crimson' }}>Could not load payslips: {error}</p>;

  return (
    <section>
      <h2>My payslips</h2>
      <table cellPadding="8" style={{ borderCollapse: 'collapse', width: '100%' }}>
        <thead>
          <tr style={{ textAlign: 'left', borderBottom: '2px solid #ccc' }}>
            <th>Period</th><th>Basic</th><th>Allowances</th><th>Deductions</th><th>Tax</th><th>Net pay</th><th />
          </tr>
        </thead>
        <tbody>
          {payslips.map((p) => (
            <tr key={p.id} style={{ borderBottom: '1px solid #eee' }}>
              <td>{p.periodYear}-{String(p.periodMonth).padStart(2, '0')}</td>
              <td>{peso(p.basicPay)}</td>
              <td>{peso(p.allowances)}</td>
              <td>{peso(p.deductions)}</td>
              <td>{peso(p.tax)}</td>
              <td><strong>{peso(p.netPay)}</strong></td>
              <td><button onClick={() => payslipService.downloadPdf(p)}>Download PDF</button></td>
            </tr>
          ))}
          {payslips.length === 0 && (
            <tr><td colSpan="7">No payslips yet.</td></tr>
          )}
        </tbody>
      </table>
    </section>
  );
}
